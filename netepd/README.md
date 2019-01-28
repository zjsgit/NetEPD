#注意事项

LINUX下的MYSQL默认是要区分表名大小写的。
　　让MYSQL不区分表名大小写的方法其实很简单：
　　1.用ROOT登录，修改/etc/my.cnf
　　2.在[mysqld]下加入一行：lower_case_table_names=1
　　3.重新启动数据库即可
$ service mysqld restart 重启即可解决