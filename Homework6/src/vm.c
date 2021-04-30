/**
 * Demonstration C program illustrating how to perform file I/O for vm assignment.
 *
 * Input file contains logical addresses
 * 
 * Backing Store represents the file being read from disk (the backing store.)
 *
 * We need to perform random input from the backing store using fseek() and fread()
 *
 * This program performs nothing of meaning, rather it is intended to illustrate the basics
 * of I/O for the vm assignment. Using this I/O, you will need to make the necessary adjustments
 * that implement the virtual memory manager.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

const int PAGE_MASK = 0xff00; // in decimal this is 15 in binary this is 1111
const int OFFSET_MASK = 0xff; //10 BITS
// number of characters to read for each line from input file
#define BUFFER_SIZE         10

// number of bytes to read
#define CHUNK               256

FILE    *address_file;
FILE    *backing_store;

// how we store reads from input file
char    address[BUFFER_SIZE];

int     logical_address;

// the buffer containing reads from backing store
signed char     buffer[CHUNK];

// Global Variables
const int PAGESIZE = 256;
const int FRAMESIZE = 256;
const int TLB_ENTRIES = 16;
int pageTable[256];
int tlb[16][2];
char physMem[256][256];
int value;
int offset;
int pageNumber;
int physAddress;
int frame;
double hitNum;
double missNum;
double addrNum;
int counter = 0;
int elementCount = 0;


int main(int argc, char *argv[])
{
    int i = 0;
    // perform basic error checking
    if (argc != 3) {
        fprintf(stderr,"Usage: ./vm [backing store] [input file]\n");
        return -1;
    }

    // open the file containing the backing store
    backing_store = fopen(argv[1], "rb");
    
    if (backing_store == NULL) { 
        fprintf(stderr, "Error opening %s\n",argv[1]);
        return -1;
    }

    // open the file containing the logical addresses
    address_file = fopen(argv[2], "r");

    if (address_file == NULL) {
        fprintf(stderr, "Error opening %s\n",argv[2]);
        return -1;
    }

    for (int i = 0; i < 16; i++){
        tlb[i][i] = -1;
    }

    for (int i = 0; i < PAGESIZE; i++){
        pageTable[i] = -1;
    }
    // read through the input file and output each logical address
    while (fgets(address, BUFFER_SIZE, address_file) != NULL) {
        logical_address = atoi(address);
        addrNum++;
        // AND number with MASK
        offset = logical_address & OFFSET_MASK;  

        // printf("Number = %d ", logical_address);
        // printf("Offset = %d ", offset);

        // shift the number 8 bit-positions to the right
        pageNumber = logical_address & PAGE_MASK;
        pageNumber = pageNumber >> 8;
        // number >> 4 = 100100111 -> 000010010
        // printf("Page Number = %d \n",pageNumber);
        //printf("addrNum: %f\n", addrNum);
        // check tlb first
        frame = -1;
        for (int i; i< TLB_ENTRIES; i++){
            if (pageNumber == tlb[i][0]){
                printf("Here\n");
                frame = tlb[i][1];
                hitNum++;
            }
        }
        if(frame == -1){
            missNum++;
            if (pageTable[pageNumber] != -1){
                    frame = pageTable[pageNumber];
                    // add to tlb 
                }
            else {
                // first seek to byte CHUNK in the backing store
                // SEEK_SET in fseek() seeks from the beginning of the file
                if (fseek(backing_store, PAGESIZE * pageNumber, SEEK_SET) != 0) {
                    fprintf(stderr, "Error seeking in backing store\n");
                    return -1;
                }
                // now read CHUNK bytes from the backing store to the buffer
                if (fread(&physMem[counter], sizeof(signed char), FRAMESIZE, backing_store) == 0) {
                    fprintf(stderr, "Error reading from backing store\n");
                    return -1;
                }
                pageTable[pageNumber] = counter;
                frame = pageTable[pageNumber]; 
                counter++;
            }  
            // printf("Element Count: %d\n", elementCount);
            tlb[elementCount][0] = pageNumber;
            tlb[elementCount][1] = frame;
            elementCount++;

            if (elementCount == TLB_ENTRIES){
                elementCount = 0;
            }
            
        }
        value = physMem[frame][offset];       
        printf("%d\n", value);
    
        double pageFaultRate = missNum/addrNum;
        double hitRate = hitNum/addrNum;
        printf("Page Fault Rate %f\n", pageFaultRate);
        printf("TLB Hit Rate %f\n", hitRate);

    }
    fclose(address_file);
    fclose(backing_store);

    return 0;
}

