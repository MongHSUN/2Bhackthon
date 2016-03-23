import socket
import gps

# Connecting to server
HOST = '140.112.30.32'
PORT = 50007
soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
soc.connect((HOST, PORT))

#Listen on gpsd on port 2947
gp = gps.gps("localhost", "2947")
gp.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)

while True:
	try:
		report = gp.next()
		if report['class'] == 'TPV':
			data = "%.6f %.6f\n" % (gp.fix.latitude,gp.fix.longitude)
			print gp.utc
			print data
			soc.sendall(data)
	except KeyError:
			pass
	except KeyboardInterrupt:
			soc.close
			quit()
	except StopIteration:
			gp = None
			print "GPSD has terminated"
soc.close
			
