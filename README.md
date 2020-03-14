### Intro

This project is mainly for file uploading, file downloading and file converting. It`s built based on Spring boot , and it uses Swagger2.0 as interfaces page(for I am way to lazy to write a web page)

### File Uploading

The main purpose of this function is that I wanna transport file between servers. The reason I chose FTP over HTTP is that HTTP has limitation about file uploading, especially large files like vedio file. But after some searching some studies, HTTP can be used to transport large files, but it will involve multiple threads working scenario, as well as resuming from breaking point. That will be heavy work for me. Thus, FTP is quite more convenient to use.

First of all, we have to initialize a FTP server, On my mac, I use `QuickFTP Server` to start a server that listens to port 21. Then I can upload files through this port. All of this above is done on my local machine, it requires to take further tests to make sure it run well on a remote machine.

The limitation of uploading file size is described in the application.properties. it`s 5000MB by default

> spring.servlet.multipart.max-file-size = 5000MB
>
> spring.servlet.multipart.max-request-size = 5000MB

Combining it with MySql seems to be quite handy. MyBatis is considerated, for a information display and recorded at the same time.





### File Downloading

At the beginning, I used FTP as means to download specific files in the server. But I found that I have to run the application locally to make connection to the FTP server. Of course, we have to connect the FTP server through server`s port 21.

In order to download the file from a regular Browser, I replaced ftp by http. More of it, on the frame of Spring boot, I specific the download direction in the application.properties, where the application can located the file. if a file is located by name, a download procedure is initilized.

First, it will set the response`s header. Then FileInput and FileOutput comes later.

There is one thing we have pay attention to. If we read a file contains chinese character, the content of the file will somehow display error codes. Therefore, we have to specific about the charset at the headers, `GBK` or `UTF-8` will solve this.

Another problem comes up that if the file is way to large, downloading process can be done. But when it comes to open it, it cannot be open. As far as i know at  the moment, the reason why the file is cannot be open is that file was written somewhere wrong, I cannot solve this problem at the moment. If you know the solution to this problem, your any modification will be welcomed.

PS: `.txt` is working, `img` and `mkv` is not working(by reading it)

PSS: the file always downloaded in a wired name, and not in the file type it should be. Anyone can help here?

Solution to PSS, the reason a file is downloaded in a weird name and double the size, it`s because the Swagger2-UI, if we go through this by normal http request, the file will be downloaded in right name and proper size





### File Converting

Converting file into the specific format requires a third-party module written in C, is ffmpeg.

In order to call ffmpeg, JNI(Java Native Interface) is required. Therefore,  calling ffmpeg at command line, which can be achieved by running java`s Runtime class.  That is the main idea of this little feature



