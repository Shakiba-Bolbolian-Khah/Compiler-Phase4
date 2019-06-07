.class public class_test1
.super java/lang/Object
.method public <init>()V
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public h(III)I
.limit locals 100
.limit stack 1000
ldc 2
istore 4
ldc 3
istore 5
iload 4
iload 5
iadd
istore 6
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 6
invokevirtual java/io/PrintStream/println(I)V
iload 1
iload 2
iadd
istore 6
iload 1
iload 2
iload 3
iadd
iadd
istore 8
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 6
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 8
invokevirtual java/io/PrintStream/println(I)V
iload 1
iload 2
imul
iload 3
imul
istore 9
iload 1
iload 2
iload 3
iadd
imul
ldc 2
idiv
iload 2
iadd
iload 1
isub
istore 10
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 9
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 10
invokevirtual java/io/PrintStream/println(I)V
iload 1
iload 2
isub
iload 3
isub
istore 11
iload 1
iload 2
iload 3
isub
isub
istore 12
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 11
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 12
invokevirtual java/io/PrintStream/println(I)V
iload 3
iload 2
idiv
iload 1
idiv
istore 13
iload 3
iload 2
iload 1
idiv
idiv
istore 14
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 13
invokevirtual java/io/PrintStream/println(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 14
invokevirtual java/io/PrintStream/println(I)V
ldc 6
ireturn
.end method
