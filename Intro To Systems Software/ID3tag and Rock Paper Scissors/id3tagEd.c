//Amm364 Alex Mitro MW 1PM
#include <stdio.h>
#include <string.h>

struct tag {
	
	char tag[3];
	char title[30];
	char artist[30];
	char album[30];
	char year[4];
	char comment[28];
	char zbs;
	char track_num;
	char genre;
};

int main(int argc,char *argv[]){
	FILE *file;
	if (argc==1){
		printf("No file found.\n");
		return -1;
	}
	else if (argc==2){
		file = fopen(argv[1],"r+b");
		fseek(file,-128,SEEK_END);
		struct tag t;
		fread(&t,1,sizeof(struct tag),file);
		if (strncmp(t.tag,"TAG",3)==0){	
			printf("%-3.3s\n",t.tag);
			printf("%-30.30s\n",t.title);
			printf("%-30.30s\n",t.artist);
			printf("%-30.30s\n",t.album);
			printf("%-4.4s\n",t.year);
			printf("%-28.28s\n",t.comment);
			printf("%-1.1d\n",t.track_num);
			printf("%-1.1d\n",t.genre);
			fclose(file);
		}
		else {
			fclose(file);
			printf("No TAG was found.\n");
		}
	}
	else if (argc>=4 && argc%2==0){
		file = fopen(argv[1],"r+b");
                fseek(file,-128,SEEK_END);
                struct tag t;
                fread(&t,1,sizeof(struct tag),file);
		int i=2;
		int j=3;
		if (strncmp(t.tag,"TAG",3)!=0){
			strncpy(t.tag,"TAG",3);
                        strncpy(t.title,"",30);
                        strncpy(t.artist,"",30);
                        strncpy(t.album,"",30);
                        strncpy(t.year,"",4);
                        strncpy(t.comment,"",28);
                        t.zbs='\0';
                        t.track_num=atoi("");
                        t.genre=atoi("0");
                        fseek(file,-128,SEEK_END);
                        fwrite(&t,128,1,file);
		}
		while(i<argc){
			if (strncmp(argv[i],"-TITLE",6)==0){
				strncpy(t.title,argv[j],30);
				fseek(file,-125,SEEK_END);
				fwrite(&t.title,30,1,file);
			}
			else if(strncmp(argv[i],"-ARTIST",7)==0){
				strncpy(t.artist,argv[j],30);
				fseek(file,-95,SEEK_END);
				fwrite(&t.artist,30,1,file);
			}
			else if(strncmp(argv[i],"-ALBUM",6)==0){
				strncpy(t.album,argv[j],30);
				fseek(file,-65,SEEK_END);
				fwrite(&t.album,30,1,file);
			}
			else if(strncmp(argv[i],"-YEAR",5)==0){
				strncpy(t.year,argv[j],4);
				fseek(file,-35,SEEK_END);
				fwrite(&t.year,4,1,file);
			}
			else if(strncmp(argv[i],"-COMMENT",8)==0){
				strncpy(t.comment,argv[j],28);
				fseek(file,-31,SEEK_END);
				fwrite(&t.comment,28,1,file);
			}
			else if(strncmp(argv[i],"-TRACK",6)==0){
				t.track_num=atoi(argv[j]);
				fseek(file,-2,SEEK_END);
				fwrite(&t.track_num,1,1,file);
			}
			i=i+2;
			j=j+2;
		}
		fclose(file);
	}
	else{
		printf("Argument count invalid.\n");
		printf("Here are the arguments you can use to change the TAG:\n");
		printf("-TITLE\n-ARTIST\n-ALBUM\n-YEAR\n-COMMENT\n-TRACK\n");
	}
	return 0;
}
