package com.example.springboot.example.bind;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

@Component("defaultDataBinder")
public class DefaultDataBinder implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(java.util.Date.class, new PropertyEditorSupport() {
			private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				try {
					setValue(dateFormat.parse(text));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			@Override
			public String getAsText() {
				Date target = (Date) getValue();
				return target == null ? "" : dateFormat.format(target);
			}
		});
	}
}
