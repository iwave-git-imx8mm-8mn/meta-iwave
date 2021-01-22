# Copyright (c) 2020 iWave Systems Technologies Pvt. Ltd.

DESCRIPTION = "iwtest files"
PROVIDES += "${PN}"
LICENSE = "GPLv2+"

COMPATIBLE_MACHINE = "(imx8mm-iwg34m|imx8mm-iwg34m-2gb|imx8mn-iwg37m|imx8mn-iwg37m-2gb)"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

LIC_FILES_CHKSUM = "file://GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "file://mipi_dsi_rotate.sh file://GPL-2 file://tfr.mp4 file://imx8mm_m4_TCM_hello_world_2gb.bin file://imx8mm_m4_TCM_hello_world.bin file://imx8mm_m4_TCM_rpmsg_lite_pingpong_rtos_linux_remote_2gb.bin file://imx8mm_m4_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin file://imx8mm_m4_TCM_rpmsg_lite_str_echo_rtos_2gb.bin file://imx8mm_m4_TCM_rpmsg_lite_str_echo_rtos.bin file://imx8mn_m7_TCM_hello_world_2gb.bin file://imx8mn_m7_TCM_hello_world.bin file://imx8mn_m7_TCM_rpmsg_lite_pingpong_rtos_linux_remote_2gb.bin file://imx8mn_m7_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin file://imx8mn_m7_TCM_rpmsg_lite_str_echo_rtos_2gb.bin file://imx8mn_m7_TCM_rpmsg_lite_str_echo_rtos.bin"
FILES_${PN} = "iwtest"
S = "${WORKDIR}"

inherit deploy
addtask deploy before do_build after do_install
do_deploy () {
    install -m 0644 ${S}/imx8mm_m4_TCM_hello_world_2gb.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mm_m4_TCM_hello_world.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mm_m4_TCM_rpmsg_lite_pingpong_rtos_linux_remote_2gb.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mm_m4_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mm_m4_TCM_rpmsg_lite_str_echo_rtos_2gb.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mm_m4_TCM_rpmsg_lite_str_echo_rtos.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mn_m7_TCM_hello_world_2gb.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mn_m7_TCM_hello_world.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mn_m7_TCM_rpmsg_lite_pingpong_rtos_linux_remote_2gb.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mn_m7_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mn_m7_TCM_rpmsg_lite_str_echo_rtos_2gb.bin ${DEPLOYDIR}
    install -m 0644 ${S}/imx8mn_m7_TCM_rpmsg_lite_str_echo_rtos.bin ${DEPLOYDIR}
}

do_install() {
    mkdir -p ${D}/iwtest
    install -m 0644 ${S}/tfr.mp4 ${D}/iwtest
    install -m 0644 ${S}/mipi_dsi_rotate.sh ${D}/iwtest
    chmod   +x ${D}/iwtest/*
}
