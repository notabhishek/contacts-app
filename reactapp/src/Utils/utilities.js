export function hashCode(st) {
    // console.log(s)
    if(!st || st === undefined) return 0
    let s = st.toString()
    let hash = 0;
    for(let i = 0, h = 0; i < s.length; i++)
        hash = Math.imul(31, hash) + s.charCodeAt(i) | 0;

    return hash;
}