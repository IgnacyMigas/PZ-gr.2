
###############################################################################
# szybki Makefile
#################

CXX=g++
RM=rm

APPNAME = sensor

SRCDIR=source
BUILDDIR=build/obj
TARGETDIR=build/bin
SOURCE=main.cpp

runSensor: 
	./$(TARGETDIR)/$(APPNAME)

sensor: 
	mkdir -p $(TARGETDIR)
	$(CXX) $(SRCDIR)/$(SOURCE) -o $(TARGETDIR)/$(APPNAME)

clean:
	$(RM) $(TARGETDIR)/$(APPNAME)
