<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简易聊天</title>
<%
	String url = "ws://" + request.getLocalAddr() + ":"
			+ request.getLocalPort() + request.getContextPath()
			+ "/example";
	pageContext.setAttribute("url", url);
%>
</head>
<body>
	<input type="text" id="name" placeholder="请输入名称">
	<button onclick="join()">確定</button>
	<table style="width: 90%;" style="display: none;" id="maintable">
		<tr>
			<td id="messages" style="border: 1px buttonface solid;" width="80%">&nbsp;</td>
			<td id="users" style="border: 1px buttonface solid;" width="20%">
				<ul id="users">

				</ul>
			</td>
		<tr>
		<tr>
			<td colspan="2"><textarea rows="20" cols="250" id="tosendmessage"></textarea>
				<button onclick="send()">发送</button></td>
		</tr>
	</table>
	<script type="text/javascript">
		function join() {
			var name = document.getElementById("name").value
					.replace(/\s+/g, '');
			var url = "${url}?name=" + name;
			if (name == '') {
				alert('名称不能为空');
				return;
			}
			if ('WebSocket' in window) {
				ws = new WebSocket(url);
			} else if ('MozWebSocket' in window) {
				ws = new MozWebSocket(url);
			} else {
				alert('Unsupported.');
			}

			ws.onopen = function() {
				ws.buffer = '';
				ws.startIndex = 0;
			};

			ws.onmessage = function(event) {
				ws.buffer = ws.buffer.substring(ws.startIndex);
				ws.buffer += event.data;

				var buffer = ws.buffer;
				while (true) {
					var start = buffer.indexOf('~~##');
					var end = buffer.indexOf('##~~');

					if (start == -1 || end == -1) {
						break;
					} else {
						var response = buffer.substring(start + 4, end);
						buffer = buffer.substring(end + 4);
						ws.startIndex = end + 4;
						handle(JSON.parse(response));
					}
					break;
				}
			};

			ws.onclose = function() {
				alert('呜嘎嘎嘎');
			};
		}

		function handle(response) {
			if (response.type == 'message') {
				var show = document.getElementById('messages');
				var p = document.createElement("p");
				var node = document.createTextNode(response.detail);
				p.appendChild(node);
				show.appendChild(p);

			} else if (response.type == 'userlist') {
				var elements = document.getElementById('users');
				var users = response.detail.split(',');
				var childs = elements.childNodes;

				for ( var i = childs.length - 1; i >= 0; i--) {
					elements.removeChild(childs[i]);
				}

				for ( var i = 0; i < users.length; i++) {
					var element = document.createElement("li");
					element.innerHTML = users[i];
					elements.appendChild(element);
				}
			}
		}

		function send() {
			var text = document.getElementById('tosendmessage').value;
			if (!text.match(/\s+/g)) {
				ws.send(text);
			}
			document.getElementById('tosendmessage').innerText = '';
		}
	</script>
</body>
</html>