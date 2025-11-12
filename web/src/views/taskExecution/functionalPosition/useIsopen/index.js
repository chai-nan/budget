import {ref} from "vue"
export function useIsopen() {
    const isopen = ref(true)

    const changeIsopen=()=>{
        isopen.value=!isopen.value
    }
    return {
        isopen,
        changeIsopen
    }
}