#!/bin/bash

if [ $# -eq 0 ]; then
  namespace=millet
else
  namespace=$1
fi

alias oc='oc -n $namespace'
alias kubectl='kubectl -n $namespace'
alias kp='kubectl get pods -o wide'
alias kd='kubectl get deployments -o wide'
alias ks='kubectl get service -o wide'
alias kl='kubectl logs'
alias kcj='kubectl get cronjob -o wide'

alias i='istioctl -n $namespace'

