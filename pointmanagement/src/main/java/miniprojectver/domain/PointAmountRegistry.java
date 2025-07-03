package miniprojectver.domain;

public final class PointAmountRegistry {

    private PointAmountRegistry() {}

    public static Class<?> typeToClass(String type) {
        switch (type) {
            case "MemberJoined":                 return MemberJoined.class;
            case "BasicPointGranted":            return BasicPointGranted.class;
            case "BonusPointGranted":            return BonusPointGranted.class;
            case "PointPurchaseRequested":       return PointPurchaseRequested.class;
            case "SubscriptionRequested":        return SubscriptionRequested.class;  // ✅ 추가됨
            case "SubscriptionStatusChecked":    return SubscriptionStatusChecked.class;
            case "BookPurchaseRequested":        return BookPurchaseRequested.class;
            case "PointDeducted":                return PointDeducted.class;
            case "PointUseFailed":               return PointUseFailed.class;
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}
