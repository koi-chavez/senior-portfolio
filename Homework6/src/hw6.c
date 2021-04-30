/**
 * Homework 6
 * CMPT-351
 * Koi Chavez
*/
#include <stdlib.h>
#include <stdio.h>

// Global Variables
int pageSize = 256;
int frameSize = 256;
int tlbEntries = 16;
int pageTable[256];
char physMem[256][256];

int main(int argc, char *argv[])
{   
    int i;
    unsigned int number;
    unsigned int test;
    const int PAGE_MASK = 0xff00; // in decimal this is 15 in binary this is 1111
    const int OFFSET_MASK = 0xff; //10 BITS

	int offset;
	int pageNumber;
	int physAddress;

	for (int i = 0; i < pageSize; i++){
			pageTable[i] = -1;
		}

    for (i = 1; i < argc; i++) {
        number = atoi(argv[i]);
    
    // AND number with MASK
        offset = number & OFFSET_MASK;  

        printf("Number = %d ", number);
        printf("Offset = %d ", offset);

        // shift the number 8 bit-positions to the right
		pageNumber = number & PAGE_MASK;
        pageNumber = pageNumber >> 8;
        // number >> 4 = 100100111 -> 000010010
        printf("Page Number = %d \n",pageNumber);

		// Set up page table

    }
	

	// pageTable[i][0] = pageNumber;
	// pageTable[i][1] = frameSize;

	// printf("pageNum = %d \n",pageNumber);
	// printf("frameSize = %d \n",frameSize);

	// // phys page number * pageSize + offset
	// physAddress = pageNumber * frameSize + offset;
	// printf("physAddress = %d \n",physAddress);
    return 0;

	
}