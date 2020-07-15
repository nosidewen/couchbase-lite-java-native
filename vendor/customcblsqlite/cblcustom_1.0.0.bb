# recipe for building libcbljavasqlitecustom.so
CLEANBROKEN = "1"
DESCRIPTION = "libcbljavasqlitecustom"
SECTION = "libcbljavasqlitecustom"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR="r0"
BB_DONT_CACHE = "1"
TARGET_CC_ARCH += "${LDFLAGS}"
do_configure[depends] += "sqlite3:do_populate_sysroot"

FILES_${PN} += " \
  ${libdir} \
"
FILES_${PN} += " \
  ${includedir} \
"
SRC_URI = "file://src/*"
S = "${WORKDIR}"

EXTRA_OEMAKE = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' \
   'CFLAGS=${CFLAGS} -I${S}/. -DWITHOUT_XATTR' 'BUILDDIR=${S}'"

do_compile() {
	echo "compile for libcbljavasqlitecustom without gradle"
	cd src
	oe_runmake  DESTDIR=${D} BINDIR=${bindir} SBINDIR=${sbindir} \
      		MANDIR=${mandir} INCLUDEDIR=${includedir}
	cd ..
}

do_install() {
    echo "trying this install libcbljavasqlitecustom files in /usr/lib/"
    install -d ${D}${libdir}
    oe_soinstall src/libcbljavasqlitecustom.so.1.0.0 ${D}${libdir}
}
