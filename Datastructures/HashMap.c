#include <stdio.h>
#include <string.h>
#include <stdbool.h>

char names[6500][1000][100];
long hashes[6500];
char null[100];


// generates hash for a string
long generateHash(char name[100]){
  long hash = 33;
  for(int i=0 ; i<strlen(name) ; i++){
    hash = hash*31 + name[i];
  }
  hash = hash%6501;
  if(hash < 0){
    return hash*-1;
  }
  return hash;
}

// returns boolean value of if the name is stored in the names array or not
bool find(char name[100]){
  int index = generateHash(name);
  int output[2];
  for(int i=0 ; i<sizeof(index) ; i++){
    if(strcmp(names[index][i], name) == 0){
      return true;
    }
  }
  return false;
}

//
void add(char name[100]){
  int index = generateHash(name);
  int index2;
  for(int i=0 ; i<sizeof(names[index]) ; i++){
    if(strlen(names[index][i]) == 0){
      index2 = i;
      break;
    }
  }
  for(int i=0 ; i<strlen(name) ; i++){
    names[index][index2][i] = name[i];
  }
}

void removeName(char name[100]){
  int index = generateHash(name);
  int index2;
  for(int i=0 ; i<sizeof(names[index]) ; i++){
    if(strcmp(names[index][i], name) == 0){
      index2 = i;
      break;
    }
  }
  for(int i=0 ; i<strlen(name) ; i++){
    names[index][index2][0] = '\0';
  }
}

int main(int argc, char *argv[]){
   FILE *nameFile;
   char name1[100];
   char name2[100];
   int counter1 = 1;

   nameFile = fopen("Names/names.txt","r");

   if (!nameFile){
     return 1;
   }

   fscanf(nameFile, "\"%[^\"]\"", name1);
   add(name1);

   while(fscanf(nameFile, ",\"%[^\"]\"", name2) != EOF){
     int hash = generateHash(name2);
     add(name2);
     counter1 += 1;
   }

   // test the hash map here


   fclose(nameFile);

   return(0);
}
