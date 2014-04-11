include "../struct/Persion.thrift"

namespace java com.example.thrift.server

typedef Persion.Persion Persion
 
service PersionService{
	Persion get(string id)
	void insert(1:Persion persion)
}