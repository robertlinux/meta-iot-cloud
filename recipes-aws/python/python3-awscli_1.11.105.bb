inherit setuptools3 update-alternatives
require python-awscli.inc

do_install_append() {
	sed -i -e '1s|^#!.*|#!/usr/bin/env python3|' ${D}${bindir}/aws
}

ALTERNATIVE_${PN} = "aws"
ALTERNATIVE_LINK_NAME[aws] = "${bindir}/aws"
ALTERNATIVE_PRIORITY = "30"
