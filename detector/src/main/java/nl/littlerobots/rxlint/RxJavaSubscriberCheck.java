package nl.littlerobots.rxlint;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

public class RxJavaSubscriberCheck implements SubscribeDetector.SubscriberCheck {
    @Override
    public boolean isMissingOnError(PsiMethod method) {
        PsiClass clz = method.getContainingClass();
        if ("rx.Observable".equals(clz.getQualifiedName()) || "rx.Single".equals(clz.getQualifiedName())) {
            return method.getParameterList().getParametersCount() == 1 &&
                    method.getParameterList().getParameters()[0].getType().equalsToText("rx.functions.Action1");
        } else if ("rx.Completable".equals(clz.getQualifiedName())) {
            // only a completion callback
            return method.getParameterList().getParametersCount() == 1 && method.getParameterList().getParameters()[0].getType().equalsToText("rx.functions.Action0");
        }
        return false;
    }
}
