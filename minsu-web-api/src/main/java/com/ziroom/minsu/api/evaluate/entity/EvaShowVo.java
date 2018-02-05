package com.ziroom.minsu.api.evaluate.entity;

import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/14.
 * @version 1.0
 * @since 1.0
 */
public class EvaShowVo extends TenantEvaVos {

    /**
     * 评价状态
     */
    private Integer pjStatus  = EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode();


    /**
     * 评价显示按钮
     */
    private String pjButton = "";
    
    /**
     * 邀请Ta评价按钮，
     */
    private String invitEvalButton = "";

    public Integer getPjStatus() {
        return pjStatus;
    }

    public void setPjStatus(Integer pjStatus) {
        this.pjStatus = pjStatus;
    }

    public String getPjButton() {
        return pjButton;
    }

    public void setPjButton(String pjButton) {
        this.pjButton = pjButton;
    }

	public String getInvitEvalButton() {
		return invitEvalButton;
	}

	public void setInvitEvalButton(String invitEvalButton) {
		this.invitEvalButton = invitEvalButton;
	}
    
}
