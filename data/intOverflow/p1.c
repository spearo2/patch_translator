#include <stdio.h>
#include <stdlib.h>


int main(void) {
  char *ptr_h;
  char h[64];

  ptr_h = getenv("HOME"); // src
  if (ptr_h != NULL) {
    sprintf(h, "%s", ptr_h); // BO // snk
    printf("%s\n", h);
  }

    return 0;
}
