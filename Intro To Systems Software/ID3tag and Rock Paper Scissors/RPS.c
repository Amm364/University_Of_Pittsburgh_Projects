//Amm364 Alex Mitro MW 1PM
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
int main()
{
    char reply[4];
    char choice[10];
    char c_choice[10];
    int win_count=0;
    int lose_count=0;
    srand(time(NULL));
    printf("Welcome to Rock, Paper, Scissors\n\n");
    printf("Would you like to play? ");
    scanf("%s",&reply);
    if (strcmp(reply,"yes")==0){
      while(strncmp(reply,"yes",3)==0){
        while(win_count<3 && lose_count<3){
            printf("What is your choice? ");
            scanf("%s",&choice);
	    if (strncmp(choice,"rock",4)==0 || strncmp(choice,"paper",5)==0 || strncmp(choice,"scissors",8)==0){
	    printf("You chose %s\n",choice);
            int c = rand()%(11+1);
            if (c<4){
                strcpy(c_choice,"rock");
             }
            else if (c>=4 && c<8){
            	strcpy(c_choice,"paper");
            }
            else {
            	strcpy(c_choice,"scissors");
            }
            printf("The computer chose %s. ",c_choice);
	    if (strcmp(c_choice,choice)==0){
		printf("It was a tie.\n");
            }
	    else if (c<4 && strncmp(choice,"paper",5)==0){
	        printf("You won this game!\n");
		win_count++;
	    }
	    else if (c<4 && strncmp(choice,"scissors",8)==0){
		printf("You lost this game.\n");
		lose_count++;
	    }
	    else if (c>=4 && c<8 && strncmp(choice,"rock",4)==0){
		printf("You lost this game.\n");
		lose_count++;
	    }
	    else if (c>=4 && c<8 && strncmp(choice,"scissors",8)==0){
		printf("You won this game!\n");
		win_count++;
	    }
	    else if (c>=8 && strncmp(choice,"paper",5)==0){
		printf("You lost this game.\n");
		lose_count++;
	    }
	    else if (c>=8 && strncmp(choice,"rock",4)==0){
		printf("You won this game!\n");
		win_count++;
	    }
	    printf("The score is now you: %d computer: %d\n",win_count,lose_count);
	    }
 	    else{
		printf("Incorrect choice.\n");
	    }   
	}
	if (win_count==3){
	    printf("Congratulations! You won! Play again? ");
	}
	else if (lose_count==3){
	    printf("Sorry. You lose. Play again? ");
	}
	scanf("%s",&reply);
	win_count=0;
	lose_count=0;
      }
     }   
    return 0;
  }


