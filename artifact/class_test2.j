.class public class_test2
.super java/lang/Object
.method public <init>()V
.limit locals 100
.limit stack 10000
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public main()I
.limit locals 100
.limit stack 10000
ldc 3
newarray int
astore 1
aload 1
arraylength
ldc 1
isub
istore 2
WHILELABEL0:
iload 2
ldc 1
ineg
if_icmpgt GTLABEL2
iconst_0
goto GTCOMPLETE3
GTLABEL2:
iconst_1
GTCOMPLETE3:
ifeq ENDWHILELABEL1
aload 1
iload 2
iload 2
iastore
iload 2
iconst_1
isub
istore 2
goto WHILELABEL0
ENDWHILELABEL1:
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 1
invokestatic java/util/Arrays/toString([I)Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
ldc 0
ireturn
.end method
