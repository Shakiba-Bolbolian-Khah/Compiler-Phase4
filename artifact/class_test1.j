.class public class_test1
.super java/lang/Object
.field private gg [I
.field private ssss Ljava/lang/String;
.field public isInF Z
.method public <init>()V
aload_0
invokespecial java/lang/Object/<init>()V
aload_0
ldc ""
putfield class_test1/ssss Ljava/lang/String;
aload_0
iconst_0
putfield class_test1/isInF Z
return
.end method
.method public f()Ljava/lang/String;
.limit locals 100
.limit stack 1000
aload_0
invokevirtual class_test1/g()Ljava/lang/String;
areturn
.end method
.method public g()Ljava/lang/String;
.limit locals 100
.limit stack 1000
ldc "A"
areturn
.end method
.method public loop(I)Z
.limit locals 100
.limit stack 1000
iconst_0
ireturn
.end method
.method public h(III)I
.limit locals 100
.limit stack 1000
ldc 2

ldc 3

iadd

ldc 6
ireturn
.end method
