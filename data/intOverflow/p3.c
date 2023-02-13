#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BLKSIZE 1024

typedef union {
  char c[BLKSIZE]
  unsigned short n[MAXBLKS]
}

BLK tmlblk;

int main(int argc, char *argv[]) {

  str = getenv("HOME"); // src
if (strlen(str) >= sizeof(tmpblk.c)) // Expected Patch // PL
  return 1;
  if (str) {
    sprintf(tmpblk.c, "%s%c%s", str, SLASH, HMEXRC); // BO // snk
  }

  return 0;
}
