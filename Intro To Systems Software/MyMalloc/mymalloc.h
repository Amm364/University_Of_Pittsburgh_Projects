// Alex Mitro Amm364
#include <stdio.h>
#include <stdlib.h>
struct node {
        int size_of_chunk;
        int used;
        struct node *next_node;
        struct node *prev_node;
};

struct node *first_node=NULL;
struct node *last_node=NULL;

void* my_bestfit_malloc(int size){
        struct node *current_node;
        struct node new_node;
        struct node *best=NULL;
        int best_size=0;
        size+=sizeof(struct node);

        if (first_node==NULL){
                first_node=(struct node*)sbrk(size);
                last_node=first_node;
                new_node.size_of_chunk=size;
                new_node.used=1;
                new_node.next_node=NULL;
                new_node.prev_node=NULL;
                *first_node=new_node;
                return (void*)first_node+sizeof(struct node);
        }
        else{
                current_node=first_node;
                while(current_node!=NULL){
                        if (current_node->used==0 && current_node->size_of_chunk >=size){
                                if (current_node->size_of_chunk==size){
                                                current_node->used=1;
                                                return (void*)current_node+sizeof(struct node);
                                }
                                else if(current_node->size_of_chunk > size) {
                                        if ((current_node->size_of_chunk < best_size) || (best_size == 0)) {
                                                best = current_node;
                                                best_size = current_node->size_of_chunk;
                                        }
                                        current_node = current_node->next_node;
                                }
                                else {
                                        current_node = current_node->next_node;
                                }
                        }
                        else {
                                current_node=current_node->next_node;
                        }
                }
                if (best==NULL){
                        struct node temp_node;
                        current_node=(struct node*)sbrk(size);
                        temp_node.prev_node=last_node;
                        temp_node.next_node=NULL;
                        temp_node.size_of_chunk=size;
                        temp_node.used=1;
                        *current_node=temp_node;
                        last_node->next_node=current_node;
                        last_node=current_node;
                        return (void*)current_node+sizeof(struct node);
                }
                else{
                        current_node=(struct node*)(best+size);
                        struct node leftover_node;
                        leftover_node.size_of_chunk=best->size_of_chunk-size;
                        leftover_node.used=0;
                        leftover_node.next_node=best->next_node;
                        leftover_node.prev_node=best;
                        *current_node=leftover_node;
                        current_node->next_node->prev_node=current_node;

                        best->size_of_chunk=size;
                        best->used=1;
                        best->next_node=current_node;
                        return (void*)best+sizeof(struct node);
                }
        }
}
my_free(void* pointer){
        struct node* freed_node;
        struct node* p_node=NULL;
        struct node* n_node=NULL;
        struct node merge_node;
        if (pointer != NULL){
                freed_node=(struct node*)(pointer-sizeof(struct node));
                p_node=freed_node->prev_node;
                n_node=freed_node->next_node;
                freed_node->used=0;
                if (p_node==NULL && n_node==NULL){
                        sbrk(0-freed_node->size_of_chunk);
                        first_node=NULL;
                        last_node=NULL;
                }
                else  if((p_node==NULL) && (n_node->used==0)){
                        freed_node->size_of_chunk=(freed_node->size_of_chunk)+(n_node->size_of_chunk);
                        freed_node->used=0;
                        freed_node->next_node=n_node->next_node;
                        n_node->next_node->prev_node=freed_node;
                }
                else if (n_node==NULL && p_node!=NULL){
                        if (p_node->used==1){
                                last_node=p_node;
                                p_node->next_node=NULL;
                                sbrk(0-freed_node->size_of_chunk);
                        }
                        else if (p_node->used==0){
                                if (p_node->prev_node==NULL){
                                        last_node=NULL;
                                        first_node==NULL;
                                        freed_node->prev_node->next_node=NULL;
                                        sbrk(0-freed_node->size_of_chunk-(p_node->size_of_chunk));
                                }
                                else if (p_node->prev_node!=NULL){
                                        last_node=p_node->prev_node;
                                        p_node->prev_node->next_node=NULL;
                                        sbrk(0-(freed_node->size_of_chunk)-(p_node->size_of_chunk));
                                }
                        }
                }
                else if ((p_node->used==0) && (n_node->used==0)){
                        p_node->size_of_chunk=(p_node->size_of_chunk)+(n_node->size_of_chunk)+(freed_node->size_of_chunk);
                        p_node->used=0;
                        p_node->next_node=n_node->next_node;
                        n_node->next_node->prev_node=p_node;
                }
                else if ((p_node->used==0) && (n_node->used==1)){
                        p_node->size_of_chunk=(freed_node->size_of_chunk)+(p_node->size_of_chunk);
                        p_node->used=0;
                        p_node->next_node=n_node;
                        n_node->prev_node=p_node;
                }
                else if ((p_node->used==1) && (n_node->used==0)){
                        freed_node->size_of_chunk=(freed_node->size_of_chunk)+(n_node->size_of_chunk);
                        freed_node->used=0;
                        freed_node->next_node=n_node->next_node;
                        n_node->next_node->prev_node=freed_node;
                }
        }
}
