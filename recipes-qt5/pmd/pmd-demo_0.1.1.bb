DESCRIPTION = "Patient Monitor Demo"

HOMEPAGE = "http://www.mentor.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "qtbase"

#SRC_URI = "file://PatientMonitorDemo.zip"
SRC_URI = "git://github.com/challinan/pmd-demo;branch=exp-12801024"
# 30476778 is the baseline, be977207 is the new 1280x1024 experiment
#SRCREV = "30476778e1682458339bdb5fdd494ed9cd67ad98"
SRCREV = "be9772076359924dea677d421ce315bd4aa33abb"
SRC_URI += "file://pmd.desktop"

# Add supporting files: desktop launcher, systemd services
SRC_URI += "file://pmd.desktop \
            file://session \
            file://16x16_heartbeat.png \
            file://22x22_heartbeat.png \
            file://32x32_heartbeat.png \
            file://48x48_heartbeat.png \
            file://64x64_heartbeat.png"

#S = "${WORKDIR}/git"

inherit qmake5

# Set path of qt5 headers as qmake5_base.bbclass sets this to just ${includedir} but
# actually it is ${includedir}/qt5
OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

FILES_${PN}-dbg += "${OE_QMAKE_PATH_EXAMPLES}/HAMPDemo/PatientMonitorDemo/.debug"
FILES_${PN} += "${OE_QMAKE_PATH_EXAMPLES}"
FILES_${PN} += "/lib/systemd/system/*"
# Pick up the dot-file
FILES_${PN} += "${ROOT_HOME}/.matchbox/*"
FILES_${PN} += "${datadir}/icons/Sato/*"

do_install_append () {
        install -d -m 755 ${D}/${datadir}/applications
        install -m 644 ${WORKDIR}/pmd.desktop ${D}/${datadir}/applications/pmd.desktop
	install -d -m 755 ${D}/${bindir}
	install -m 755 ${B}/Release/PatientMonitorDemo ${D}/${bindir}

	# Install the ${HOME}/.matchbox/sesssion file to launch demo
	install -d -m 755 ${D}/${ROOT_HOME}/.matchbox
	install -m 755 ${WORKDIR}/session ${D}/${ROOT_HOME}/.matchbox

	# Install custom icons for desktop launcher
	install -d -m 755 ${D}/${datadir}/pixmaps
	install -m 644 ${WORKDIR}/64x64_heartbeat.png ${D}/${datadir}/pixmaps
	install -m 644 ${WORKDIR}/48x48_heartbeat.png ${D}/${datadir}/pixmaps
	install -m 644 ${WORKDIR}/32x32_heartbeat.png ${D}/${datadir}/pixmaps
	install -m 644 ${WORKDIR}/22x22_heartbeat.png ${D}/${datadir}/pixmaps
	install -m 644 ${WORKDIR}/16x16_heartbeat.png ${D}/${datadir}/pixmaps
	ln -s 64x64_heartbeat.png ${D}/${datadir}/pixmaps/heartbeat.png
}
