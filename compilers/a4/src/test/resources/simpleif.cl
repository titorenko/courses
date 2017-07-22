class Main inherits IO {
  fibo(i:Int):Int {
    if i=0 then
      0
    else
      1 
    fi
  };
    
  main():Object {
  	{
    out_int(fibo(0));
    out_int(fibo(1));
    out_int(fibo(0+1));
    out_int(fibo(0+0));
    }
  };
};