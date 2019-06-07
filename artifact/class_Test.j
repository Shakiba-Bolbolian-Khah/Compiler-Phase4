.class public class_Test
.super java/lang/Object
.field public a Ljava/lang/String;
.method public <init>()V
.limit locals 100
.limit stack 1000
aload_0
invokespecial java/lang/Object/<init>()V
aload_0
ldc ""
putfield class_Test/a Ljava/lang/String;
return
.end method
.method public main()I
.limit locals 100
.limit stack 1000
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_0
getfield class_Test/a Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
ldc 5
istore_1
ldc 3
istore_2
ldc 0
ireturn
.end method
