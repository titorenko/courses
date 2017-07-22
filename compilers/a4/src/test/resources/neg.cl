class Main inherits IO {
  b: Bool;
  
  main(): Object { {
  	if (not b) then
   		out_string("works")
   	else
   		out_string("wrong")
   	fi;
   	
   	if (b) then
   		out_string("wrong")
   	else
   		out_string("works")
   	fi;
   	
  }};
};