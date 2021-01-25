# Copyright (C) 2020 iWave Systems Technologies Pvt Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

KERNEL_BRANCH ?= "imx8mm_8mn"
LOCALVERSION = "-2.1.0"
KERNEL_SRC ?= "git://github.com/iwave-git-imx8mm-8mn/kernel_iwg34m.git;protocol=https"
KERNEL_SRC1 ?= "git://github.com/iwave-git-imx8mm-8mn/kernel_iwg37m.git;protocol=https"

SRC_URI = "${@bb.utils.contains_any('MACHINE', 'imx8mm-iwg34m imx8mm-iwg34m-2gb', '${KERNEL_SRC};branch=${KERNEL_BRANCH}', bb.utils.contains_any('MACHINE', 'imx8mn-iwg37m imx8mn-iwg37m-2gb', '${KERNEL_SRC1};branch=${KERNEL_BRANCH}', '', d), d)}"

SRCREV = "${@bb.utils.contains_any('MACHINE', 'imx8mm-iwg34m imx8mm-iwg34m-2gb', '62c0186302d2c4754e06722b3c38f6c4557b91f4', bb.utils.contains_any('MACHINE', 'imx8mn-iwg37m imx8mn-iwg37m-2gb', '53fa2b80287b96265d744654ec42d830629df2ec', '', d), d)}"

FILES_${KERNEL_PACKAGE_NAME}-base += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo "

KERNEL_CONFIG_COMMAND = "oe_runmake_call -C ${S} CC="${KERNEL_CC}" O=${B} olddefconfig"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEFAULT_PREFERENCE = "1"

DO_CONFIG_V7_COPY = "no"
DO_CONFIG_V7_COPY_mx6 = "yes"
DO_CONFIG_V7_COPY_mx7 = "yes"
DO_CONFIG_V7_COPY_mx8 = "no"

addtask copy_defconfig after do_patch before do_preconfigure

do_copy_defconfig () {
    install -d ${B}
    if [ ${DO_CONFIG_V7_COPY} = "yes" ]; then
        # copy latest imx_v7_defconfig to use for mx6, mx6ul and mx7
        mkdir -p ${B}
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/.config
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/../defconfig
    else
        # copy latest iw_rainbowgxxx_defconfig to use for mx8
        mkdir -p ${B}
	if [ "${@bb.utils.contains_any('MACHINE', 'imx8mm-iwg34m imx8mm-iwg34m-2gb', 'yes', 'no', d)}" = "yes" ]; then
		cp ${S}/arch/arm64/configs/iw_rainbowg34m_defconfig ${B}/.config
		cp ${S}/arch/arm64/configs/iw_rainbowg34m_defconfig ${B}/../defconfig
	fi
	if [ "${@bb.utils.contains_any('MACHINE', 'imx8mn-iwg37m imx8mn-iwg37m-2gb', 'yes', 'no', d)}" = "yes" ]; then
		cp ${S}/arch/arm64/configs/iw_rainbowg37m_defconfig ${B}/.config
		cp ${S}/arch/arm64/configs/iw_rainbowg37m_defconfig ${B}/../defconfig
	fi
    fi
}

DELTA_KERNEL_DEFCONFIG ?= ""
#DELTA_KERNEL_DEFCONFIG_prepend_mx8 = "sdk_imx.config "

do_merge_delta_config[dirs] = "${B}"
do_merge_delta_config() {
    for deltacfg in ${DELTA_KERNEL_DEFCONFIG}; do
        if [ -f ${S}/arch/${ARCH}/configs/${deltacfg} ]; then
            ${KERNEL_CONFIG_COMMAND}
            oe_runmake_call -C ${S} CC="${KERNEL_CC}" O=${B} ${deltacfg}
        elif [ -f "${WORKDIR}/${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${WORKDIR}/${deltacfg}
        elif [ -f "${deltacfg}" ]; then
            ${S}/scripts/kconfig/merge_config.sh -m .config ${deltacfg}
        fi
    done
    cp .config ${WORKDIR}/defconfig
}
addtask merge_delta_config before do_preconfigure after do_copy_defconfig

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
