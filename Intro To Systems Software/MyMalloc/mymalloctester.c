// Alex Mitro Amm364
#include <stdlib.h>
#include <stdio.h>
#include <mymalloc.h>
#define MALLOC my_bestfit_malloc
//replace free here with the appropriate version of myfree
#define FREE my_free
//THESE ARE A FEW TESTS I USED TO MAKE SURE MALLOC AND FREE WORKED.

void main(){
	printf("This will test normal malloc and free functions in order. No best_fit tests.\n\n");
	printf("%p\n",sbrk(0));
	void* hold=my_bestfit_malloc(17);
	printf("%p\n",sbrk(0));
	void* hold1=my_bestfit_malloc(18);
        printf("%p\n",sbrk(0));
	void* hold2=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
	void* hold3=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
	void* hold4=my_bestfit_malloc(23);
        printf("%p\n",sbrk(0));
	void* hold5=my_bestfit_malloc(20);
        printf("%p\n",sbrk(0));
	void* hold6=my_bestfit_malloc(28);
        printf("%p\n",sbrk(0));
	void* hold7=my_bestfit_malloc(21);
        printf("%p\n",sbrk(0));
	void* hold8=my_bestfit_malloc(6);
        printf("%p\n",sbrk(0));
	void* hold9=my_bestfit_malloc(12);
        printf("%p\n",sbrk(0));
	void* hold10=my_bestfit_malloc(22);
        printf("%p\n",sbrk(0));
	void* hold11=my_bestfit_malloc(17);
        printf("%p\n",sbrk(0));
	void* hold12=my_bestfit_malloc(20);
        printf("%p\n",sbrk(0));
	void* hold13=my_bestfit_malloc(55);
        printf("%p\n",sbrk(0));
	void* hold14=my_bestfit_malloc(20);
        printf("%p\n",sbrk(0));
	void* hold15=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
	my_free(hold15);
	my_free(hold14);
	my_free(hold13);
	my_free(hold12);
	my_free(hold11);
	my_free(hold10);
	my_free(hold9);
	my_free(hold8);
	my_free(hold7);
	my_free(hold6);
	my_free(hold5);
	my_free(hold4);
	my_free(hold3);
	my_free(hold2);
	my_free(hold1);
	my_free(hold);

	printf("%p\n\n\n",sbrk(0));
	printf("Testing the merging of free'd nodes.\n\n");
 	printf("%p\n",sbrk(0));
        hold=my_bestfit_malloc(17);
        printf("%p\n",sbrk(0));
        hold1=my_bestfit_malloc(18);
        printf("%p\n",sbrk(0));
        hold2=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
        hold3=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
        hold4=my_bestfit_malloc(23);
        printf("%p\n",sbrk(0));
        hold5=my_bestfit_malloc(20);
        printf("%p\n",sbrk(0));
        hold6=my_bestfit_malloc(28);
        printf("%p\n",sbrk(0));
        hold7=my_bestfit_malloc(21);
        printf("%p\n",sbrk(0));
        hold8=my_bestfit_malloc(6);
        printf("%p\n",sbrk(0));
        hold9=my_bestfit_malloc(12);
        printf("%p\n",sbrk(0));
        hold10=my_bestfit_malloc(22);
        printf("%p\n",sbrk(0));
        hold11=my_bestfit_malloc(17);
        printf("%p\n",sbrk(0));
        hold12=my_bestfit_malloc(20);
        printf("%p\n",sbrk(0));
        hold13=my_bestfit_malloc(55);
        printf("%p\n",sbrk(0));
        hold14=my_bestfit_malloc(20);
        printf("%p\n",sbrk(0));
        hold15=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
        my_free(hold9);
        my_free(hold11);
        my_free(hold10);
	void* hold16=my_bestfit_malloc(40);
	printf("%p\nIt didn't extend the heap. hold 9 10 and 11 must have merged to make enough room.\n",sbrk(0));
        my_free(hold16);
        my_free(hold15);
        my_free(hold14);
        my_free(hold13);
        my_free(hold12);
        my_free(hold8);
        my_free(hold7);
        my_free(hold6);
        my_free(hold5);
        my_free(hold4);
        my_free(hold3);
        my_free(hold2);
        my_free(hold1);
        my_free(hold);
	printf("%p\nIt reached the starting address again. Free worked.\n\n",sbrk(0));

	
	printf("Testing to see if freeing the last node with free nodes before it will move the brk correctly.\n\n");
        printf("%p\n",sbrk(0));
        hold=my_bestfit_malloc(17);
        printf("%p\n",sbrk(0));
        hold1=my_bestfit_malloc(18);
        printf("%p\n",sbrk(0));
        hold2=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
        hold3=my_bestfit_malloc(15);
        printf("%p\n",sbrk(0));
        hold4=my_bestfit_malloc(23);
        printf("%p\n",sbrk(0));
        hold5=my_bestfit_malloc(20);
        printf("%p this is the brk point that we should get if it works.\n",sbrk(0));
        hold6=my_bestfit_malloc(28);
        printf("%p\n",sbrk(0));
        hold7=my_bestfit_malloc(21);
        printf("%p is the brk point for the heap before the freeing.\n",sbrk(0));
	my_free(hold6);
	my_free(hold7);
	printf("%p this is the correct brk point. It works.\n",sbrk(0));
	
}
