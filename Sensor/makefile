
###############################################################################
# szybki Makefile
#################

CXX=g++
RM=rm

SRCDIR=source
BUILDDIR=build/obj
TARGET=build/bin/sensor
SOURCE=main.cpp

runSensor: 
	./$(TARGET)

sensor: 
	$(CXX) $(SRCDIR)/$(SOURCE) -o $(TARGET) 

clean:
	$(RM) $(TARGET)
