# Copyright (C) 2020 iWave Systems Technologies Pvt Ltd
require recipes-security/optee-imx/optee-os.imx.inc

SRCBRANCH = "imx_5.4.24_2.1.0"
SRCREV = "7a49776de59265500f10a247125429fde1555ac1"

SRC_URI = "${@bb.utils.contains_any('MACHINE', 'imx8mm-iwg34m imx8mm-iwg34m-2gb', '${OPTEE_OS_SRC};branch=${SRCBRANCH} file://PATCH000-iW-PRFYZ-SC-01-R1.0-REL1.0-Linux5.4.24_Optee_Basic_Customization.patch', bb.utils.contains_any('MACHINE', 'imx8mn-iwg37m imx8mn-iwg37m-2gb', '${OPTEE_OS_SRC};branch=${SRCBRANCH} file://PATCH000-iW-PRGJZ-SC-01-R1.0-REL1.0-Linux5.4.24_Optee_Basic_Customization.patch', '', d), d)}"

PLATFORM_FLAVOR_mx8mm = "${@bb.utils.contains('MACHINE', 'imx8mm-iwg34m', 'mx8mm_iwg34m', 'mx8mm_iwg34m_2gb', d)}"

PLATFORM_FLAVOR_mx8mn = "${@bb.utils.contains('MACHINE', 'imx8mn-iwg37m', 'mx8mn_iwg37m', 'mx8mn_iwg37m_2gb', d)}"

COMPATIBLE_MACHINE = "(imx8mm-iwg34m|imx8mm-iwg34m-2gb|imx8mn-iwg37m|imx8mn-iwg37m-2gb)"
