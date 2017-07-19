#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
int main(){
	char arguments[512];
	char* token;
	char* check;
	const char *args[100];
	int i=0;
	int index=0;
	int rtv;
	int type;
	int found=0;
	int f=0;
	FILE* file_num;
	while(1){
		i=0;
		printf("myshell: ");					//print out shell
		fgets(arguments,512,stdin);
		token=strtok(arguments," \t\n");
		
	        if (strcmp(token,"exit") == 0){				//if exit, then exit
			exit(0);
                }
		else if (strcmp(token,"cd") == 0){			//change directory
			int i=0;
                        token=strtok(NULL," \t\n");
			if (strcmp(token,"..")==0){
				chdir("..");
			}
			else{
				rtv=chdir(token);
			}
			if (rtv==-1){
				printf("Invalid directory.\n");		//directory error
			}
		}
		else{
	                while(token!=NULL){  
			       args[i++]=token;
	                       token=strtok(NULL," \t\n");		
	                }						//get arguments
	                args[i]=NULL;
			int file=i-1;
			int id=fork();					//fork it
			int status;
			if (id!=0){
				wait(&status);				//wait for child to finish
			}
			else{
				for (i=0;args[i]!=NULL;i++){
                                	if (strcmp(args[i],">")==0){				//check if it's a file operation
                                      	file_num=freopen(args[file],"w",stdout);
                                        	args[i]=NULL;
                                        	break;
                                	}
                                	else if (strcmp(args[i],">>")==0){
                                      	file_num=freopen(args[file],"a",stdout);
                                        	args[i]=NULL;
                                        	break;
                                	}
                                	else if (strcmp(args[i],"<")==0){
                                      		file_num=freopen(args[file],"r",stdin);
                                        	args[i]=NULL;
                                        	break;
                                	}
                        	}
				int ret=execvp(args[0],args);		//execvp the command
				if (ret==-1){
					printf("Invalid command.\n");
					exit(0);
				}
			}
		}
	}
	return 0;
}
