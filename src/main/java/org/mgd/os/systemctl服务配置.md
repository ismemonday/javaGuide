[回到主目录](/README.md)
# systemctl服务配置
#service配置文件分为[Unit]、[Service]、[Install]三个部分。

#[Unit]部分：指定服务描述、启动顺序、依赖关系，包括Description、Documentation、After、Before、Wants、Requires。
[Unit]
#Description指定当前服务的简单描述。
Description=nginx代理服务

#Documentation指定服务的文档，可以是一个或多个文档的URL，可选，一般不用配置该项。
Documentation=http://nginx.org/en/docs

#启动顺序，After和Before。
#注意，After和Before字段只涉及启动顺序，不涉及依赖关系。

#After表示当前服务在network.target之后启动，可以指定多个服务，以空格隔开。
After=network.target sshd.service
After=sshd-keygen.service

#Before表示当前服务在tomcat.target之前启动，可以设置多个，以空格隔开，可选，根据实际需要配置。
Before=tomcat.service

#依赖关系，Wants和Requires，可选，根据实际需要配置。
#Wants为"弱依赖"关系，即如果"mysqld.service"启动失败或停止运行，不影响nginx.service继续执行。
#Requires为"强依赖"关系，即如果"mysqld.service"启动失败或异常退出，那么nginx.service也必须退出。
#想要添加多个服务，可以多次使用此选项，也可以设置一个空格分隔的服务列表。
#注意，Wants与Requires只涉及依赖关系，与启动顺序无关，默认情况下是同时启动的。
Wants=mysqld.service
Requires=mysqld.service

#[Service]部分：指定启动行为，包括Type、EnvironmentFile、ExecStart、ExecReload、ExecStop、PrivateTmp。
[Service]
#Type指定服务的启动类型，必须为simple, exec, forking, oneshot, dbus, notify, idle 之一。常用simple和forking。
# simple（默认值）：ExecStart启动的进程为该服务主进程。
# exec：exec与simple类似，不同之处在于，只有在该服务的主服务进程执行完成之后，systemd才会认为该服务启动完成。 其他后继单元必须一直阻塞到这个时间点之后才能继续启动。
# forking：ExecStart将以fork()方式启动，此时父进程将会退出，子进程将成为主进程。
# oneshot：oneshot与simple类似，不同之处在于，只有在该服务的主服务进程退出之后，systemd才会认为该服务启动完成，才会开始启动后继单元。 此种类型的服务通常需要设置RemainAfterExit=选项。当Type= 与 ExecStart=都没有设置时，Type=oneshot 就是默认值。
# dbus：类似于simple，但会等待D-Bus信号后启动。
# notify：类似于simple，启动结束后会发出通知信号，然后 Systemd 再启动其他服务。
# idle：类似于simple，但是要等到其他任务都执行完，才会启动该服务。一种使用场合是为让该服务的输出，不与其他服务的输出相混合。
# 建议对长时间持续运行的服务尽可能使用Type=simple(这是最简单和速度最快的选择)。注意，因为simple类型的服务 无法报告启动失败、也无法在服务完成初始化后对其他单元进行排序，所以，当客户端需要通过仅由该服务本身创建的IPC通道(而非由systemd创建的套接字或D-bus之类)连接到该服务的时候，simple类型并不是最佳选择。在这种情况下， notify或dbus(该服务必须提供D-Bus接口)才是最佳选择， 因为这两种类型都允许服务进程精确的安排何时算是服务启动成功、何时可以继续启动后继单元。notify类型需要服务进程明确使用sd_notify()函数或类似的API，否则，可以使用forking作为替代(它支持传统的UNIX服务启动协议)。最后，如果能够确保服务进程调用成功、服务进程自身不做或只做很少的初始化工作(且不大可能初始化失败)，那么exec将是最佳选择。注意，因为使用任何 simple 之外的类型都需要等待服务完成初始化，所以可能会减慢系统启动速度。 因此，应该尽可能避免使用 simple 之外的类型(除非必须)。另外，也不建议对长时间持续运行的服务使用 idle 或 oneshot 类型。
Type=forking

#EnvironmentFile指定当前服务的环境参数文件。该文件内部的key=value键值对，可以用$key的形式，在当前配置文件中获取。
EnvironmentFile=/etc/nginx/nginx.conf

#启动命令
# ExecStart指定启动进程时执行的命令。
# ExecReload指定当该服务被要求重新载入配置时所执行的命令。另外，还有一个特殊的环境变量 $MAINPID 可用于表示主进程的PID，例如可以这样使用：/bin/kill -HUP $MAINPID。强烈建议将 ExecReload= 设为一个能够确保重新加载配置文件的操作同步完成的命令行。
# ExecStop指定停止服务时执行的命令。
# ExecStartPre指定启动服务之前执行的命令。不常用。
# ExecStartPost指定启动服务之后执行的命令。不常用。
# ExecStopPost指定停止服务之后执行的命令。不常用。
ExecStart=/usr/sbin/nginx -c /etc/nginx/nginx.conf
ExecReload=/usr/local/nginx/sbin/nginx -s reload
ExecStop=/usr/local/nginx/sbin/nginx -s quit

# 设为 true表示在进程的文件系统名字空间中挂载私有的 /tmp 与 /var/tmp 目录， 也就是不与名字空间外的其他进程共享临时目录。 这样做会增加进程的临时文件安全性，但同时也让进程之间无法通过 /tmp 或 /var/tmp 目录进行通信。
# 适用于web系统服务，不适用于mysql之类的数据库用户服务，数据库用户服务设为false。
PrivateTmp=true

#[Install]部分：指定服务的启用信息，只有在systemctl的enable与disable命令在启用/停用单元时才会使用此部分。
[Install]
# “WantedBy=multi-user.target”表示当系统以多用户方式（默认的运行级别）启动时，这个服务需要被自动运行。
WantedBy=multi-user.target