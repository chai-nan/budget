import{ref} from 'vue'
import {reportingLog} from "@/api/taskExecution/index"

export function useLog(){
    const logList=ref('')
    const getLogList=async(itemId,id,taskId)=>{
        const res=await reportingLog({itemId,id,taskId})
        logList.value=res.data
    }
    return {
        logList,getLogList
    }
    
}