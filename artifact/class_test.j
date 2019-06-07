.class public class_test
.super class_test2
.field private k I
.method public <init>()V
.limit locals 100
.limit stack 10000
aload_0
invokespecial class_test2/<init>()V
aload_0
iconst_0
putfield class_test/k I
return
.end method
.method public s(Lclass_test2;)Z
.limit locals 100
.limit stack 10000
aload 1
getfield class_test2/j I
aload_0
getfield class_test/j I
if_icmpne EQUALLABEL2
iconst_1
goto EQUALCOMPLETE3
EQUALLABEL2:
iconst_0
EQUALCOMPLETE3:
ifeq ELSELABEL4
iconst_1
ireturn

ELSELABEL4:
iconst_0
ireturn
CONDITIONCOMPLETE5:
.end method
