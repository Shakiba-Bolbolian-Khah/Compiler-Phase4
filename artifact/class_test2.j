.class public class_test2
.super java/lang/Object
.field public j I
.method public <init>()V
.limit locals 100
.limit stack 10000
aload_0
invokespecial java/lang/Object/<init>()V
aload_0
iconst_0
putfield class_test2/j I
return
.end method
.method public main()I
.limit locals 100
.limit stack 10000
aload_0
ldc 10
putfield class_test2/j I
new class_test
dup
invokespecial class_test/<init>()V
astore 1
aload 1
ldc 3
putfield class_test/j I
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 1
getfield class_test/j I
invokevirtual java/io/PrintStream/println(I)V
new class_test2
dup
invokespecial class_test2/<init>()V
astore 2
aload 2
ldc 3
putfield class_test2/j I
aload 1
aload 2
invokevirtual class_test/s(Lclass_test2;)Z
ifeq ELSELABEL0
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "equal"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto CONDITIONCOMPLETE1
ELSELABEL0:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "not equal"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
CONDITIONCOMPLETE1:
ldc 0
ireturn
.end method
.method public k()I
.limit locals 100
.limit stack 10000
ldc 5
ireturn
.end method
