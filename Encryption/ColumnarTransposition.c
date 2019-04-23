#include <stdio.h>
#include <string.h>

// this is the key used for encrypting the text
char key[] = "lovelace";

char table[1000][100];
int lineCounter = 0;

void swapColumns(int c1, int c2){
  // swap letters in the key string
  char a = key[c1];
  key[c1] = key[c2];
  key[c2] = a;

  // swap the columns in the encryption table
  for(int i=0 ; i<=lineCounter ; i++){
    a = table[i][c1];
    table[i][c1] = table[i][c2];
    table[i][c2] = a;
  }
}

int main(int argc, char *argv[]){
  FILE *text;
  int c;
  int counter1 = 0;
  int sorted = 0;
  int swaps = 0;
  text = fopen("Q7/text.txt","r");

  if (!text){
    return 1;
  }

  // fill the encryption table with the text
  while ( (c = fgetc(text)) != EOF ){
    if(counter1 == strlen(key)){
      lineCounter += 1;
      counter1 = 0;
    }
    table[lineCounter][counter1] = (char)c;
    counter1 += 1;
  }

  // fill out the rest of the table with Xs if it is not full
  if(counter1 < strlen(key)){
    for(int i=counter1 ; i<strlen(key) ; i++){
      table[lineCounter][i] = 'x';
    }
  }

  // encrypt text using Columnar Transposition Cipher
  sorted = 0;
  while(sorted == 0){
    swaps = 0;
    for(int i=0 ; i<strlen(key)-1 ; i++){
      if(key[i] > key[i+1]){
        // should swap the letters
        swaps += 1;
        swapColumns(i, i+1);
      }
    }
    if(swaps == 0){
      sorted = 1;
    }
  }

  for(int i=0 ; i<=lineCounter ; i++){
    for(int o=0 ; o<strlen(key) ; o++){
      printf("%c", table[i][o]);
    }
  }
}
