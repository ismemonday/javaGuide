@echo off
@rem mrs_install.exe执行后会调用当前文件执行注册服务程序
color 0a
cd %~dp0..
set MRS_HOME=%cd%
set MYSQL_PATH=%MRS_HOME%
set MYSQL_ZIP_FILE=%MRS_HOME%\install\mysql-8.0.26-winx64.zip
set UNZIP_FILE=%MRS_HOME%\install\unzip.exe
set JDK_ZIP_FILE="%MRS_HOME%\install\jdk-11.0.7.zip"
set JDK_PATH=%MYSQL_PATH%
set JDK_BIN_PATH=%JDK_PATH%\jdk-11.0.7\bin
set MYSQL_BIN_PATH=%MYSQL_PATH%\mysql-8.0.26-winx64\bin
set JAVA_CMD=%JDK_BIN_PATH%\java.exe
set MYSQL_CMD=%MYSQL_BIN_PATH%\mysql.exe

@rem 检查是否存在java指令
echo start check jdk....
%JAVA_CMD% -version >nul 2>&1
if "%ERRORLEVEL%" == "0"  (echo jdk exist&goto check_mysql)  else (goto unzip_jdk)

:check_mysql
echo start check mysql...
%MYSQL_CMD% --version >nul 2>&1
if "%ERRORLEVEL%" == "0"  (echo mysql exist&goto install_mrs)  else (goto unzip_mysql)

:unzip_jdk
@rem 解压jdk
%UNZIP_FILE% -o %JDK_ZIP_FILE% -d  %JDK_PATH%
echo unzip jdk success.....
@rem 注册环境变量
setx Path "%JDK_BIN_PATH%;%Path%" /m
goto check_mysql

:unzip_mysql
@rem 解压myql
%UNZIP_FILE% -o %MYSQL_ZIP_FILE% -d  %MYSQL_PATH%
echo unzip mysql success.....
@rem 注册环境变量
setx Path "%MYSQL_BIN_PATH%;%Path%" /m
@rem 注册服务
net stop mysql
sc.exe delete mysql
"%MYSQL_BIN_PATH%\mysqld.exe" --initialize-insecure
"%MYSQL_BIN_PATH%\mysqld.exe" --install "mysql"
sc config mysql start=auto
net start mysql
ping -n 5 127.1>nul
echo "update sql password..."
"%MYSQL_CMD%" -u root -e "use mysql;ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';FLUSH PRIVILEGES;"
goto install_mrs

:install_mrs
@rem 安装mrs服务
echo try to install mrs serve
net stop mrs
"%MRS_HOME%\install\mrs.exe" uninstall
"%MRS_HOME%\install\mrs.exe" install
net start MRS
