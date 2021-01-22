# Copyright 2017-2019 NXP
 
DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "imx_5.4.24_2.1.0"
IMX_MKIMAGE_SRC ?= "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https"

SRC_URI = "${@bb.utils.contains('MACHINE', 'imx8mm-iwg34m', '${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} file://PATCH003-iW-PRFYZ-SC-01-R1.0-REL1.0-Linux5.4.24_imx-mkimage_Basic_Customization.patch', bb.utils.contains('MACHINE', 'imx8mm-iwg34m-2gb', '${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} file://PATCH003-iW-PRFYZ-SC-01-R1.0-REL1.0-Linux5.4.24_imx-mkimage_Basic_Customization_2GB.patch', bb.utils.contains('MACHINE', 'imx8mn-iwg37m', '${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} file://PATCH003-iW-PRGJZ-SC-01-R1.0-REL1.0-Linux5.4.24_imx-mkimage_Basic_Customization.patch', bb.utils.contains('MACHINE', 'imx8mn-iwg37m-2gb', '${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} file://PATCH003-iW-PRGJZ-SC-01-R1.0-REL1.0-Linux5.4.24_imx-mkimage_Basic_Customization_2GB.patch', '', d), d), d), d)}"

SRCREV = "6745ccdcf15384891639b7ced3aa6ce938682365"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
