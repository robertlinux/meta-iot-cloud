DESCRIPTION = "Node-RED nodes for connecting to the IBM Internet of Things Foundation"
HOMEPAGE = "https://github.com/ibm-messaging/iot-nodered"
LICENSE = "ECL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=30b3836521b3d65bef598bbc358a3afa"

DEPENDS = "nodejs"
RDEPENDS_${PN} = "bash nodejs node-red"

SRC_URI = "git://github.com/ibm-messaging/iot-nodered.git;branch=master"
SRCREV = "${AUTOREV}"

PR = "r1"

S = "${WORKDIR}/git"

# Modules
BLUEMIX_HDFS = "${S}/node-red-contrib-bluemix-hdfs"
IBM_WATSON_IOT = "${S}/node-red-contrib-ibm-watson-iot"
IBMPUSH = "${S}/node-red-contrib-ibmpush"
SCX_IBMIOTAPP = "${S}/node-red-contrib-scx-ibmiotapp"

NODE_MODULES_DIR = "${libdir}/node_modules"
NPM_CACHE_DIR ?= "${WORKDIR}/npm_cache"
NPM_REGISTRY ?= "https://registry.npmjs.org/"
NPM_INSTALL_FLAGS = "--production"

do_compile() {
	export NPM_CONFIG_CACHE="${NPM_CACHE_DIR}"
	
	# Clear cache
	npm cache clear

	cd ${BLUEMIX_HDFS}
	npm --registry=${NPM_REGISTRY} --arch=${TARGET_ARCH} --target_arch=${TARGET_ARCH} ${NPM_INSTALL_FLAGS} install

	cd ${IBM_WATSON_IOT}
	npm --registry=${NPM_REGISTRY} --arch=${TARGET_ARCH} --target_arch=${TARGET_ARCH} ${NPM_INSTALL_FLAGS} install

	cd ${IBMPUSH}
	npm --registry=${NPM_REGISTRY} --arch=${TARGET_ARCH} --target_arch=${TARGET_ARCH} ${NPM_INSTALL_FLAGS} install

	cd ${SCX_IBMIOTAPP}
	npm --registry=${NPM_REGISTRY} --arch=${TARGET_ARCH} --target_arch=${TARGET_ARCH} ${NPM_INSTALL_FLAGS} install
}

do_install() {
	install -d ${D}${NODE_MODULES_DIR}
    	cp -r ${BLUEMIX_HDFS} ${D}${NODE_MODULES_DIR}/
	cp -r ${IBM_WATSON_IOT} ${D}${NODE_MODULES_DIR}/
	cp -r ${IBMPUSH} ${D}${NODE_MODULES_DIR}/
	cp -r ${SCX_IBMIOTAPP} ${D}${NODE_MODULES_DIR}/
}

PACKAGES = "${PN}"

FILES_${PN} += "${NODE_MODULES_DIR}"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
