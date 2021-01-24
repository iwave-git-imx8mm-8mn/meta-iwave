# Copyright (C) 2020 iWave Systems Technologies Pvt Ltd.

DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."
require u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
inherit pythonnative

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PROVIDES += "u-boot"
DEPENDS_append = " dtc-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

UBOOT_SRC ?= "git://github.com/iwave-git-imx8mm-8mn/uboot_iwg34m.git;protocol=https"
SRCBRANCH = "imx8mm_8mn"
UBOOT_SRC1 ?= "git://github.com/iwave-git-imx8mm-8mn/uboot_iwg37m.git;protocol=https"

SRC_URI = "${@bb.utils.contains_any('MACHINE', 'imx8mm-iwg34m imx8mm-iwg34m-2gb', '${UBOOT_SRC};branch=${SRCBRANCH}', bb.utils.contains_any('MACHINE', 'imx8mn-iwg37m imx8mn-iwg37m-2gb', '${UBOOT_SRC1};branch=${SRCBRANCH}', '', d), d)}"

SRCREV = "${@bb.utils.contains_any('MACHINE', 'imx8mm-iwg34m imx8mm-iwg34m-2gb', '3f183999a306d40febf38700279fbc50687561d8', bb.utils.contains_any('MACHINE', 'imx8mn-iwg37m imx8mn-iwg37m-2gb', 'dc81ba9faaf25502c59f4d554180c04fb4c62d69', '', d), d)}" 

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

LOCALVERSION ?= "-5.4.24-2.1.0"

BOOT_TOOLS = "imx-boot-tools"

do_deploy_append_mx8m () {
    # Deploy u-boot-nodtb.bin and fsl-imx8mq-XX.dtb, to be packaged in boot binary by imx-boot
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${UBOOT_CONFIG}
                fi
            done
            unset  j
        done
        unset  i
    fi

}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

UBOOT_NAME_mx6 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx7 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx8 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
