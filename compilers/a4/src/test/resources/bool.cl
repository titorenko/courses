-- bool.cl: tricky cases for unboxed bools

class Main {
  main():Object {
    let
      t:Bool <- true,
      b2:Object,
      io:IO <- new IO
    in {
      b2 <- t;
      b2.type_name();

    }
  };
};






