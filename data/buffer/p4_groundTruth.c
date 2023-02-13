#include <stdio.h>
#include <stdlib.h>
#include <string.h>


typedef union {
  char c[BLKSIZE]
  unsigned short n[MAXBLKS]
}

BLK tmlblk;

int main(int argc, char *argv[]) {

  str = getenv("HOME"); // src

  if (str) {
    snprintf(tmpblk.c, sizeof(tmpblk.c), "%s%c%s", str, SLASH, HMEXRC); // Expected Patch
  }

  return 0;
}
