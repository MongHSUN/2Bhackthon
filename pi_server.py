import socket

HOST =''
PORT = 50007

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((HOST,PORT))
s.listen(1)
print 'pi_server is ready!'
conn,addr= s.accept()
print 'Connected by',addr
while True:
    try:
        data = ''
        data = conn.recv(1024)
        if not data: break
        f = open('1.txt','a')
        f.write(data)
        print 'Receive data:',data 
        f.close()
    except KeyboardInterrupt:
        conn.close()
        quit()
conn.close()
print 'Connectiin is over!'
