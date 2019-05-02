FROM centos:latest
RUN rm -f /etc/yum.repos.d/*
ADD rhel.repo /etc/yum.repos.d

RUN yum -y install systemd; yum clean all; \
yum -y install rsyslog; \
(cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;

ADD cmd_list.sh.txt /etc/rsyslog.d

VOLUME [ "/sys/fs/cgroup" ]
CMD [ "/usr/sbin/init" ]


