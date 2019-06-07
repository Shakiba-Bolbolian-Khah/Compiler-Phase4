.class public class_test2
.super java/lang/Object
.method public <init>()V
.limit locals 100
.limit stack 1000
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public main()I
.limit locals 100
.limit stack 1000
new class_test1
dup
invokespecial class_test1/<init>()V
astore 1
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 1
ldc 1
ldc 2
ldc 4
invokevirtual class_test1/h(III)I
invokevirtual java/io/PrintStream/println(I)V
ldc 0
ireturn
.end method
