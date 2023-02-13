  #include <stdio.h>
  #include <stdlib.h>


  int main(void) {
    char *ptr_h;
    char h[64]; // main-9 ( h := (char *)alloc(SizeOf(char )*64 )

    ptr_h = getenv("HOME"); // src
    if (ptr_h != NULL) {
        sprintf(h, "Your home directory is: %s !", ptr_h); // BO // snk
        printf("%s\n", h); // main-15
    }

    return 0;
  }
