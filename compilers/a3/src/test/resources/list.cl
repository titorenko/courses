class List {
 isNil() : Bool { true };
 
 cons(i : Int) : List {
 	self
 };
 
 tail() : List { { abort(); self; } };
};

class Main inherits IO {

   mylist : List;



   main() : Object {
      {
	 mylist <- new List.cons(1).cons(2).cons(3).cons(4).cons(5);
	 while (not mylist.isNil()) loop
	    {
	       mylist <- mylist.tail();
	    }
	 pool;
      }
   };

};



