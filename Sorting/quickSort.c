#include <stdio.h>
#include <string.h>

// names array that will contain the names to be sorted
char names[10000][100];
// alphabet array to get value of letters which is there index in the array
char alphabet[26] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
'U', 'V', 'W', 'X', 'Y', 'Z'};


// adds a string element to a certain index of names
void addToNames(char string[100], int index){
  int counter2 = 0;
  for(int i=0 ; i<strlen(string) ; i++){
    names[index][counter2] = string[counter2];
    counter2 += 1;
  }
}

// finds and returns the maximum of two integers
int findMax(int a, int b){
  if(a > b){
    return a;
  }
  if(b > a){
    return b;
  }
  return a;
}

// swaps two elements with indices a and b in the names array
void swapNames(int a, int b)
{
    int lenA = strlen(names[a]);
    int lenB = strlen(names[b]);
    int max = findMax(lenA, lenB);
    char c[100];

    for(int i=0 ; i<lenA ; i++){
      c[i] = names[a][i];
    }

    for(int i=0 ; i<max ; i++){
      names[a][i] = names[b][i];
    }
    for(int i=0 ; i<max ; i++){
      names[b][i] = c[i];
    }
}

// get the value of a letter, which is there index in the alphabet array
int getIndex(char letter){
  for(int i=0 ; i<26 ; i++){
    if(letter == alphabet[i]){
      return i;
    }
  }
  return -1;
}

// partitions a section of the names array into elements that are inferior
// to the pivot element, which is the last element of the section, to the left
// of the pivot and names that are superior to the pivot element to the
// right of the pivot
void partition(int start, int end){

  if(start < end){

    int swapIndex = start;
    int nameProcessed;
    int letterIndex;

    for(int i=start ; i<=end ; i++){

      nameProcessed = 0;
      letterIndex = 0;

      while(nameProcessed == 0){
        if(letterIndex > strlen(names[i])){
          // swap the name with the name at nameIndex
          swapNames(i, swapIndex);
          swapIndex += 1;
          nameProcessed = 1;
        }
        else if(letterIndex == strlen(names[end]) && strlen(names[i]) == strlen(names[end])){
          // if the current name is the same as the pivot, swap the nme with the name at nameIndex
          swapNames(i, swapIndex);
          swapIndex += 1;
          nameProcessed = 1;
        }
        else if(letterIndex == strlen(names[end]) && strlen(names[i]) > strlen(names[end])){
          // go to next name
          nameProcessed = 1;
        }
        else if(getIndex(names[i][letterIndex]) < getIndex(names[end][letterIndex])){
          // swap name with the name at nameIndex
          swapNames(i, swapIndex);
          swapIndex += 1;
          nameProcessed = 1;
        }
        else if(getIndex(names[i][letterIndex]) > getIndex(names[end][letterIndex])){
          // go to next name
          nameProcessed = 1;
        }
        else if(getIndex(names[i][letterIndex]) == getIndex(names[end][letterIndex])){
          // go to next letter
          letterIndex += 1;
        }
      }
    }
    partition(start, swapIndex-2);
    partition(swapIndex, end);
  }
}

// main function that will read the input names file, insert each name into
// the names array,  quick sort that array, and finally print out the sorted
// names array
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
   addToNames(name1, 0);

   while(fscanf(nameFile, ",\"%[^\"]\"", name2) != EOF){
     addToNames(name2, counter1);
     counter1 += 1;
   }

   partition(0, counter1-1);
   for(int p=0 ; p<counter1 ; p++){
     printf("%s\n", names[p]);
   }
   fclose(nameFile);

   return(0);
}
