.class public class_test2
.super java/lang/Object
.field private integer I
.field private booll Z
.field private strr Ljava/lang/String;
.method public <init>()V
.limit locals 100
.limit stack 1000
aload_0
invokespecial java/lang/Object/<init>()V
aload_0
iconst_0
putfield class_test2/integer I
aload_0
iconst_0
putfield class_test2/booll Z
aload_0
ldc ""
putfield class_test2/strr Ljava/lang/String;
return
.end method
.method public main()I
.limit locals 100
.limit stack 1000
new class_test1
dup
invokespecial class_test1/<init>()V
astore_1
getstatic java/lang/System/out Ljava/io/PrintStream;
aload_1
ldc 1
ldc 2
ldc 4
invokevirtual class_test1/h(III)I
invokevirtual java/io/PrintStream/println(I)V
ldc 0
ireturn
.end method
