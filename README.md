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

…wait to be done

### File Converting

…wait to be done