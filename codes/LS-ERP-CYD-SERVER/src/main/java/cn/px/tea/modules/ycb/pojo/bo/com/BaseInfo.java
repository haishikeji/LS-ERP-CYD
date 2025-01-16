package cn.px.tea.modules.ycb.pojo.bo.com;

public class BaseInfo {
    public static BaseInfo createNew(UserInfo userInfo) {
        return new BaseInfo(userInfo.getShopId(),userInfo.getUserId(),userInfo.getCardName(),0L);
    }

    public static BaseInfo createNew(MemberInfo memberInfo) {
        return new BaseInfo(memberInfo.getShopId(),memberInfo.getMemberId(),memberInfo.getNickname(),memberInfo.getMemberId());
    }
    private Long shopId;
    private Long operator;
    private String operatorName;

    private Long membersId;

    public BaseInfo() {
    }

    public BaseInfo(Long shopId, Long operator, String operatorName, Long membersId) {
        this.shopId = shopId;
        this.operator = operator;
        this.operatorName = operatorName;
        this.membersId = membersId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getMembersId() {
        return membersId;
    }

    public void setMembersId(Long membersId) {
        this.membersId = membersId;
    }
}
