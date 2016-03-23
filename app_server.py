import socket

HOST =''
PORT = 4000

f = open('1.txt','r')
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((HOST,PORT))
s.listen(1)
print 'app_server is ready!'

while True:
    conn,addr = s.accept()
    print 'Connected by',addr
    i = -1
    while True:
        i = i - 1
        f.seek(i,2)
        if f.read(1) == '\n':
            break
    data = ''
    data = f.readline().strip()
    conn.sendall(data)
    print 'Send data:',data
    print 'A connection is over!'
    conn.close()
